#!/usr/bin/env python

import os
from oeqa.runtime.case import OERuntimeTestCase

class TestPath:
    base = os.path.join(OERuntimeTestCase.td['TOPDIR'],
                        OERuntimeTestCase.td['TMPDIR'],
                        'unittest',
                        OERuntimeTestCase.td['MACHINE'])

    @classmethod
    def get_base(cls):
        return cls.base
