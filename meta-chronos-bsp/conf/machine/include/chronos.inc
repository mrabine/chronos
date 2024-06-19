#
# Common configuration for all devices
#

# "-i 4096" is the yocto default
# "-O ^has_journal" rootfs is read only -> no journal
EXTRA_IMAGECMD_ext4 = "-i 4096 -O ^has_journal"
