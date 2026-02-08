DESCRIPTION = "chronos image"
LICENSE = "MIT"

inherit image chronos-rootfs-postcommands chronos-testimage

# ensure package index is generated everytime image is built.
addtask do_generate_package_index before do_image_qa after do_rootfs

python do_generate_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}

# ensure image contains minimum
IMAGE_LINGUAS = "c en-us"

# locale
IMAGE_INSTALL += "glibc-utils glibc-charmap-utf-8 glibc-localedata-i18n localedef"

# conversion
IMAGE_INSTALL += "glibc-gconv glibc-gconv-iso8859-1 glibc-gconv-utf-7 glibc-gconv-utf-16 glibc-gconv-utf-32"

# base
IMAGE_INSTALL += "base-files"

# init
IMAGE_INSTALL += "initng"

# base init scripts
IMAGE_INSTALL += "chronos-init"

# core
IMAGE_INSTALL += "busybox"

# device
IMAGE_INSTALL += "busybox-mdev"

# syslog
IMAGE_INSTALL += "busybox-syslog"

# random generator
IMAGE_INSTALL += "rng-tools"

# ssh server
IMAGE_INSTALL += "dropbear"

# networking
IMAGE_INSTALL += "iproute2"

# rdma
IMAGE_INSTALL += "rdma-core qperf"

# ptp
IMAGE_INSTALL += "linuxptp"

# kernel modules
IMAGE_INSTALL += "kernel-modules"

# package management
IMAGE_FEATURES += "package-management"

# extra debug tools
FEATURE_PACKAGES_chronos-debug = "chronos-debug"

# make sure libraries needed are added to the SDK
TOOLCHAIN_TARGET_TASK += "packagegroup-chronos-sdk-target"

# make sure binaries needed are added to the SDK
TOOLCHAIN_HOST_TASK += "packagegroup-chronos-sdk-host"

# image rootfs size
IMAGE_ROOTFS_SIZE = "131072"
IMAGE_ROOTFS_EXTRA_SPACE = "65536"

# image type
IMAGE_FSTYPES = 'ext4 ext4.gz'
