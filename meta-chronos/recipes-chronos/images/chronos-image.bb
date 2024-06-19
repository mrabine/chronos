DESCRIPTION = "chronos image"
IMAGE_LINGUAS = " "
LICENSE = "MIT"

inherit image

# init
IMAGE_INSTALL += "initng"

# base init scripts
IMAGE_INSTALL += "chronos-init"

# core
IMAGE_INSTALL += "busybox"

# syslog
IMAGE_INSTALL += "busybox-syslog"

# ssh
IMAGE_INSTALL += "dropbear"

# extra debug tools
FEATURE_PACKAGES_chronos-debug = "chronos-debug"

# users
inherit extrausers

EXTRA_USERS_PARAMS += "usermod -p \\\$6\\\$f9DDvk4TupAG.Q\\\$YqgvKKHaSWKy5oiPNgJmaGUZUoGypvAag8Cv4Uz177BDvwbI0aXvVMBGjV4kQI04/VFXMNAU612/Zi8aIArwq. root;"

# image
IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "4096"
IMAGE_FSTYPES = 'ext4 ext4.gz'
