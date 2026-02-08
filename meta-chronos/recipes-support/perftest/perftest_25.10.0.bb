SUMMARY = "OpenFabrics Enterprise Distribution (OFED) Performance Tests"
DESCRIPTION = "Bandwidth and latency microbenchmarks for RDMA"
LICENSE = "GPL-2.0-only | BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=9310aaac5cbd7408d794745420b94291"

DEPENDS = "rdma-core pciutils"
RDEPENDS:${PN} = "rdma-core"

inherit autotools pkgconfig

SRC_URI = "git://github.com/linux-rdma/perftest;branch=master;protocol=https"
SRCREV = "8a1d3d7234add23fe006b2ff51d650ff022077a8"

S = "${WORKDIR}/git"

EXTRA_OECONF = "--disable-static"

FILES:${PN} = "${bindir}/*"
