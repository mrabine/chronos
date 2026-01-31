#
# Common configuration for the rolex product family devices
#

require conf/machine/include/chronos.inc

# console
SERIAL_CONSOLES = "115200;ttyS0"

# kernel default preferred provider
PREFERRED_PROVIDER_virtual/kernel = "linux-yocto-rt"
