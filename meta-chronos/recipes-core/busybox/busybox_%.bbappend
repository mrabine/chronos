FILESEXTRAPATHS:prepend := "${THISDIR}/files:${THISDIR}/${PN}:"

SRC_URI += " \
    file://brctl.cfg \
"

inherit initng

INITNG_TARGETS:${PN} = " \
    daemon/syslogd \
    daemon/getty \
"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "syslog"
GROUPADD_PARAM:${PN} = "syslog"
