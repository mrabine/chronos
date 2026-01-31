FILESEXTRAPATHS:prepend := "${THISDIR}/files:${THISDIR}/${PN}:"

PACKAGECONFIG:remove = "libjitterentropy"

SRC_URI += " \
    file://daemon/rngd \
"

do_install:append(){
    install -d ${D}${initng_daemon_dir}
    install -m 0755 ${WORKDIR}/daemon/rngd ${D}${initng_daemon_dir}/rngd
}

inherit initng

INITNG_TARGETS:${PN} = " \
    daemon/rngd \
"
