#
# Common configuration for all devices
#

require conf/machine/include/qemuboot-chronos.inc

# override the linux standard base config
MACHINEOVERRIDES =. "linuxstdbase:"
