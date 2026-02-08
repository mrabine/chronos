SUMMARY = "XPMEM: Cross-Partition Memory"
DESCRIPTION = "Userspace library for XPMEM cross-partition memory access."
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS:${PN} = "kernel-module-xpmem"

inherit autotools pkgconfig

SRC_URI = "git://github.com/hpc/xpmem;branch=master;protocol=https"
SRCREV = "3bcab55479489fdd93847fa04c58ab16e9c0b3fd"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    --disable-kernel-module \
    --with-kerneldir=${STAGING_KERNEL_DIR} \
"

FILES:${PN} += " \
    ${libdir}/libxpmem.so.* \
"

FILES:${PN}-dev = " \
    ${includedir}/xpmem.h \
    ${libdir}/libxpmem.so \
    ${libdir}/pkgconfig/cray-xpmem.pc \
"

FILES:${PN}-staticdev += " \
    ${libdir}/libxpmem.a \
"
