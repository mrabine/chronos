SUMMARY = "Chronos RDMA kernel modules and tools"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN}:append = " \
    kernel-module-crc32-generic \
    kernel-module-ip6-udp-tunnel \
    kernel-module-udp-tunnel \
    kernel-module-rdma-rxe \
    kernel-module-ib-umad \
    kernel-module-xpmem \
    rdma-core \
    ucx \
    ucc \
    iproute2-rdma \
    rxe-init \
    xpmem-init \
"
