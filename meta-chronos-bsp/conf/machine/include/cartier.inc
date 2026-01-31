#
# Common configuration for the cartier product family devices
#

require conf/machine/include/chronos.inc

# console
SERIAL_CONSOLES = "115200;ttyAMA0"

# kernel default preferred provider
PREFERRED_PROVIDER_virtual/kernel = "linux-yocto-rt"
