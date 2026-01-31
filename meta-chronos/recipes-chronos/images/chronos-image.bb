DESCRIPTION = "chronos image"
LICENSE = "MIT"

inherit image chronos-testimage

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

# random generator
IMAGE_INSTALL += "rng-tools"

# core
IMAGE_INSTALL += "busybox"

# device
IMAGE_INSTALL += "busybox-mdev"

# syslog
IMAGE_INSTALL += "busybox-syslog"

# ssh server
IMAGE_INSTALL += "dropbear"

# extra debug tools
FEATURE_PACKAGES_chronos-debug = "chronos-debug"

# package management
IMAGE_FEATURES += "package-management"

# users
inherit extrausers

EXTRA_USERS_PARAMS += "usermod -p \\\$6\\\$f9DDvk4TupAG.Q\\\$YqgvKKHaSWKy5oiPNgJmaGUZUoGypvAag8Cv4Uz177BDvwbI0aXvVMBGjV4kQI04/VFXMNAU612/Zi8aIArwq. root;"

# image type
IMAGE_FSTYPES = 'ext4 ext4.gz'
