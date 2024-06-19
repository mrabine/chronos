SUMMARY = "Join framework"
DESCRIPTION = "Join is a lightweight network framework library."
HOMEPAGE = "https://joinframework.net/"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6729a3345f87d7fb915f8b3fe076c569"

DEPENDS = "openssl zlib"

SRC_URI = "git://github.com/joinframework/join;branch=main;protocol=https"
SRCREV = "aba8909e8c186155f671d97bd27bfcd834916473"

S = "${WORKDIR}/git"

inherit cmake

FILES:${PN} += " \
    ${libdir}/*.so.* \
"

FILES:${PN}-dev += " \
    ${prefix}/include/* \
    ${libdir}/pkgconfig/* \
    ${libdir}/*.so \
"
