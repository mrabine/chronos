#!/usr/bin/env python3
"""
Module that define the default class for test
"""

import unittest
from oeqa.runtime.case import OERuntimeTestCase

class TestBase(OERuntimeTestCase):
    """
    Base class for test.
    """

    def setUp(self):
        """
        Setup test case.
        """
        super(TestBase, self).setUp()

        if self.id() in self.td["TEST_CASES_SKIP"]:
            raise unittest.SkipTest(f"SKIPPED - {self.id()}")

        self.tc.logger.info(f"RUNNING - {self.id()}")
