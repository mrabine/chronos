FILESEXTRAPATHS:prepend := "${THISDIR}/files:${THISDIR}/${PN}:"

SRC_URI += " \
    file://klogd.cfg \
    file://brctl.cfg \
    file://modinfo.cfg \
    file://lspci.cfg \
    file://lsusb.cfg \
    file://netcat.cfg \
    file://ipv6.cfg \
    file://ntpd.cfg \
    file://mountpoint.cfg \
    file://daemon/mdev \
    file://daemon/syslogd \
    file://daemon/klogd \
    file://daemon/getty \
"

do_install:append(){
    if ${@bb.utils.contains('DISTRO_FEATURES','initng','true','false',d)}; then
        install -d ${D}${initng_daemon_dir}
        install -m 0755 ${WORKDIR}/daemon/mdev ${D}${initng_daemon_dir}/mdev
        install -m 0755 ${WORKDIR}/daemon/syslogd ${D}${initng_daemon_dir}/syslogd
        install -m 0755 ${WORKDIR}/daemon/klogd ${D}${initng_daemon_dir}/klogd
        install -m 0755 ${WORKDIR}/daemon/getty ${D}${initng_daemon_dir}/getty
        baud=$(echo "${SERIAL_CONSOLES}" | awk '{print $1}' | cut -d';' -f1)
        cons=$(echo "${SERIAL_CONSOLES}" | awk '{print $1}' | cut -d';' -f2)
        sed -i -e "s,@SERIAL_BAUD@,$baud,g" ${D}${initng_daemon_dir}/getty
        sed -i -e "s,@SERIAL_CONS@,$cons,g" ${D}${initng_daemon_dir}/getty
    fi
}

inherit initng

INITNG_TARGETS:${PN} = " \
    daemon/mdev \
    daemon/syslogd \
    daemon/klogd \
    daemon/getty \
"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "syslog"
GROUPADD_PARAM:${PN} = "syslog"
