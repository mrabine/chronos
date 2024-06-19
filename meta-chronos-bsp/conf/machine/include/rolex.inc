#
# Common configuration for the rolex product family devices
#

DEFAULTTUNE ?= "corei7-64"
require conf/machine/include/x86/x86-base.inc
require conf/machine/include/x86/tune-corei7.inc
require conf/machine/include/chronos.inc

# Kernel
PREFERRED_PROVIDER_virtual/kernel = "linux-rolex-rt"
