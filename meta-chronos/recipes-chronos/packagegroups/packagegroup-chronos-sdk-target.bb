SUMMARY = "Chronos target dependencies required in SDK"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN}:append = " \
    libstdc++-dev \
    googletest-dev \
    capnproto-dev \
    rdma-core-dev \
    libxpmem-dev \
    join-dev \
    ucx-dev \
    ucc-dev \
"
