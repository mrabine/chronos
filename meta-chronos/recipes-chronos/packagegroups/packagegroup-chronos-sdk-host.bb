SUMMARY = "Chronos host dependencies required in SDK"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN}:append = " \
    nativesdk-capnproto-compiler \
"
