FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS:remove = "zlib"

SRC_URI += " \
    file://service/genkey \
    file://daemon/dropbear \
"

EXTRA_OECONF += " \
    --disable-lastlog \
    --disable-utmp \
    --disable-utmpx \
    --disable-wtmp \
    --disable-wtmpx \
    --disable-zlib \
"

do_install:append() {
    touch ${D}${sysconfdir}/nologin
    chmod 0644 ${D}${sysconfdir}/nologin

    install -d ${D}${initng_service_dir}
    install -m 0755 ${WORKDIR}/service/genkey ${D}${initng_service_dir}/genkey

    install -d ${D}${initng_daemon_dir}
    install -m 0755 ${WORKDIR}/daemon/dropbear ${D}${initng_daemon_dir}/dropbear
}

inherit initng

INITNG_TARGETS:${PN} = " \
    service/genkey \
    daemon/dropbear \
"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "ssh"
GROUPADD_PARAM:${PN} = "ssh"
