SUMMARY = "chronos target dependencies required in SDK"

inherit packagegroup

RDEPENDS:${PN} = " \
    libstdc++-dev \
    googletest-dev \
    capnproto-dev \
    join-dev \
    rdma-core-dev \
"
