SUMMARY = "Chronos RDMA kernel modules"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = "\
    kernel-module-rdma-rxe \
    kernel-module-xpmem \
    rdma-core \
    iproute2-rdma \
"
