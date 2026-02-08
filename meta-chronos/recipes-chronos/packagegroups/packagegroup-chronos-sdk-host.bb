SUMMARY = "chronos host dependencies required in SDK"

inherit packagegroup

RDEPENDS:${PN} = " \
    nativesdk-capnproto-compiler \
"
