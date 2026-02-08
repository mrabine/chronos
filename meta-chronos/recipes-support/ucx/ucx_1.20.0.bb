SUMMARY = "Unified Communication X"
DESCRIPTION = "UCX is a communication library implementing high-performance messaging"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cbe4fe88c540f18985ee4d32d590f683"

DEPENDS = "libnl numactl"

inherit autotools pkgconfig

BRANCH = "v${@'.'.join(d.getVar('PV').split('.')[0:2])}.x"
SRC_URI = "git://github.com/openucx/ucx;branch=${BRANCH};protocol=https"
SRCREV = "4b7a6ca8410f9cea0e15857233ecfeefdd863dde"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    --with-rc \
    --with-ud \
    --with-dc \
    --disable-doxygen-doc \
    --disable-examples \
    --disable-static \
"

PACKAGECONFIG ??= "verbs rdmacm xpmem cma mt"

PACKAGECONFIG[verbs] = "--with-verbs=${STAGING_DIR_TARGET}/usr,--without-verbs,rdma-core"
PACKAGECONFIG[rdmacm] = "--with-rdmacm=${STAGING_DIR_TARGET}/usr,--without-rdmacm,rdma-core"
PACKAGECONFIG[xpmem] = "--with-xpmem=${STAGING_DIR_TARGET}/usr,--without-xpmem,libxpmem"
PACKAGECONFIG[cma] = "--enable-cma,--disable-cma"
PACKAGECONFIG[mt] = "--enable-mt,--disable-mt"
PACKAGECONFIG[cuda] = "--with-cuda=${STAGING_DIR_TARGET}/usr,--without-cuda,cuda"
PACKAGECONFIG[rocm] = "--with-rocm=${STAGING_DIR_TARGET}/usr,--without-rocm"
PACKAGECONFIG[java] = "--with-java,--without-java,openjdk-11-native"
PACKAGECONFIG[go] = "--with-go,--without-go,go-native"

FILES:${PN} += "${libdir}/ucx/*.so ${libdir}/ucx/*.so.*"
FILES:${PN}-dev += "${libdir}/ucx/*.la"

INSANE_SKIP:${PN} += "dev-so buildpaths"
INSANE_SKIP:${PN}-dev += "buildpaths"
