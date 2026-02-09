SUMMARY = "Soft RoCE init"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://system/rxe"

RDEPENDS:${PN} += "rdma-core iproute2-rdma"

inherit allarch

do_install() {
    install -d ${D}${initng_system_dir}
    install -m 0755 ${WORKDIR}/system/rxe ${D}${initng_system_dir}/rxe
}

inherit initng

INITNG_TARGETS:${PN} += "system/rxe"
