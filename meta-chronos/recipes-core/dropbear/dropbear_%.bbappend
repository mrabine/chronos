FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS:remove = "zlib"

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
