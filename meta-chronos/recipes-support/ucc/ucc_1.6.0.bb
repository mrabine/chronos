SUMMARY = "Unified Collective Communication"
DESCRIPTION = "UCC is a collective communication operations API and library"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0aa687785103f23ae28ca7dca25dc1ff"

DEPENDS = "autoconf-archive-native ucx"

inherit autotools pkgconfig

BRANCH = "v${@'.'.join(d.getVar('PV').split('.')[0:2])}.x"
SRC_URI = "git://github.com/openucx/ucc;branch=${BRANCH};protocol=https"
SRCREV = "87ee888b78d12d797ac8288c8214c7cb86c8bd8c"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    --with-ucx=${STAGING_DIR_TARGET}/usr \
    --disable-doxygen-doc \
    --disable-static \
"

PACKAGECONFIG ??= "verbs rdmacm"

PACKAGECONFIG[verbs] = "--with-ibverbs=${STAGING_DIR_TARGET}/usr,--without-ibverbs,rdma-core"
PACKAGECONFIG[rdmacm] = "--with-rdmacm=${STAGING_DIR_TARGET}/usr,--without-rdmacm,rdma-core"
PACKAGECONFIG[cuda] = "--with-cuda=${STAGING_DIR_TARGET}/usr,--without-cuda,cuda"
PACKAGECONFIG[rocm] = "--with-rocm=${STAGING_DIR_TARGET}/usr,--without-rocm"
PACKAGECONFIG[nccl] = "--with-nccl=${STAGING_DIR_TARGET}/usr,--without-nccl"
PACKAGECONFIG[rccl] = "--with-rccl=${STAGING_DIR_TARGET}/usr,--without-rccl"
PACKAGECONFIG[sharp] = "--with-sharp=${STAGING_DIR_TARGET}/usr,--without-sharp"

do_configure:prepend() {
    ( cd ${S} && NOCONFIGURE=1 ./autogen.sh )
}

FILES:${PN} += "${libdir}/ucc/*.so ${libdir}/ucc/*.so.*"
FILES:${PN}-dev += "${libdir}/ucc/*.la"

INSANE_SKIP:${PN} += "dev-so buildpaths"
INSANE_SKIP:${PN}-dev += "buildpaths"
