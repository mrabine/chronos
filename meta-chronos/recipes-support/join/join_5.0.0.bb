SUMMARY = "Join framework"
DESCRIPTION = "Join is a modular C++ network runtime framework for Linux."
HOMEPAGE = "https://joinframework.net/"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6729a3345f87d7fb915f8b3fe076c569"

SRC_URI = "git://github.com/joinframework/join;branch=main;protocol=https"
SRCREV = "3a3d0a2beeffe55e54198bf887df2867adb50243"

S = "${WORKDIR}/git"

inherit cmake pkgconfig unittest

AUTO_LIBNAME_PKGS = ""

PACKAGECONFIG ??= "crypto data fabric services"

PACKAGECONFIG[crypto] = "-DJOIN_ENABLE_CRYPTO=ON,-DJOIN_ENABLE_CRYPTO=OFF,openssl"
PACKAGECONFIG[data] = "-DJOIN_ENABLE_DATA=ON,-DJOIN_ENABLE_DATA=OFF,zlib"
PACKAGECONFIG[fabric] = "-DJOIN_ENABLE_FABRIC=ON,-DJOIN_ENABLE_FABRIC=OFF"
PACKAGECONFIG[services] = "-DJOIN_ENABLE_SERVICES=ON,-DJOIN_ENABLE_SERVICES=OFF,zlib"
PACKAGECONFIG[io-uring] = "-DJOIN_ENABLE_IO_URING=ON,-DJOIN_ENABLE_IO_URING=OFF,liburing"
PACKAGECONFIG[xdp] = "-DJOIN_ENABLE_XDP=ON,-DJOIN_ENABLE_XDP=OFF,xdp-tools"
PACKAGECONFIG[numa] = "-DJOIN_ENABLE_NUMA=ON,-DJOIN_ENABLE_NUMA=OFF,numactl"
PACKAGECONFIG[test] = "-DJOIN_ENABLE_TESTS=ON,-DJOIN_ENABLE_TESTS=OFF"

FILES:${PN} += "${libdir}/*.so.*"

FILES:${PN}-dev = " \
    ${includedir} \
    ${libdir}/*.so \
    ${libdir}/pkgconfig \
    ${libdir}/cmake \
"
