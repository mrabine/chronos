#!/usr/bin/env python3
"""
Module loading and running unit tests.
"""

import os
import re
import unittest
from oeqa.utils.testpath import TestPath
from oeqa.utils.pkgserver import PkgServer
from oeqa.runtime.abstract.testbase import TestBase

class UnitTestBase(TestBase):
    """
    Base class for unit test.
    """

    @classmethod
    def setUpClass(cls):
        """
        Setup unit test.
        """
        super().setUpClass()

        try:
            cls.pkg_server = PkgServer(
                tc=cls.tc,
                machine=cls.td.get('MACHINE', ''),
                arch=cls.td.get('TUNE_PKGARCH', ''),
                tmpdir=os.path.join(cls.td.get('TOPDIR', ''), cls.td.get('TMPDIR', ''))
            )
            cls.pkg_server.start_server(server_ip=cls.tc.target.server_ip)

            pkg_name = getattr(cls, 'pkg_name', None)
            if not pkg_name:
                raise Exception("missing package name for test suite")

            status, output = cls.tc.target.run(f"opkg install {pkg_name}-test")
            if status != 0:
                raise Exception(f"failed to install {pkg_name}-test:\n{output}")
        except:
            cls.tearDownClass()
            raise

    @classmethod
    def tearDownClass(cls):
        """
        Tear down unit test.
        """
        pkg_name = getattr(cls, 'pkg_name', None)
        if pkg_name:
            cls.tc.target.run(f"opkg remove {pkg_name}-test")

        cls.pkg_server.stop_server()

        super().tearDownClass()

def load_tests(loader, tests, pattern):
    """
    Load tests from files in TestPath.get_base()
    """
    base_path = TestPath.get_base()
    if not os.path.isdir(base_path):
        raise unittest.SkipTest("test base path not found")

    test_suite = unittest.TestSuite()
    test_class = {}

    for pkg_name in os.listdir(base_path):
        pkg_path = os.path.join(base_path, pkg_name)
        if not os.path.isfile(pkg_path):
            continue

        with open(pkg_path) as f:
            test_paths = [line.strip() for line in f if line.strip()]

        if not test_paths:
            continue

        class_name = f'{re.sub(r"[^a-zA-Z0-9_]", "_", pkg_name)}'
        UnitTest = type(class_name, (UnitTestBase,), {'pkg_name': pkg_name})
        test_class[class_name] = UnitTest

        for test_path in test_paths:
            test_name = f'test_{os.path.basename(test_path).replace("-", "_")}'

            def make_test(path):

                def test_method(self):
                    status, output = self.target.run(path)
                    self.assertEqual(status, 0, f"test failed :\n{output}")

                return test_method

            setattr(UnitTest, test_name, make_test(test_path))

    for test in test_class.values():
        test_suite.addTests(loader.loadTestsFromTestCase(test))

    if test_suite.countTestCases() == 0:
        raise unittest.SkipTest("no valid test cases found")

    return test_suite
