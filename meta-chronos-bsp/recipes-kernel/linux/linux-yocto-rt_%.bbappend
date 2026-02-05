FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

require recipes-kernel/linux/linux-chronos.inc

SRC_URI += " \
    file://core.cfg \
    ${@bb.utils.contains('TARGET_ARCH', 'x86_64', 'file://core-x86_64.cfg', '', d)} \
    ${@bb.utils.contains('TARGET_ARCH', 'aarch64', 'file://core-arm64.cfg', '', d)} \
    file://preempt.cfg \
    file://syscall.cfg \
    file://binfmt.cfg \
    file://security.cfg \
    file://crypto.cfg \
    file://firmware.cfg \
    ${@bb.utils.contains('TARGET_ARCH', 'x86_64', 'file://firmware-x86_64.cfg', '', d)} \
    file://storage.cfg \
    file://filesystems.cfg \
    file://scsi.cfg \
    file://pci.cfg \
    file://usb.cfg \
    file://console.cfg \
    ${@bb.utils.contains('TARGET_ARCH', 'x86_64', 'file://console-x86_64.cfg', '', d)} \
    file://display.cfg \
    file://input.cfg \
    file://netfilter.cfg \
    file://netmisc.cfg \
    file://ethernet.cfg \
    file://ipv4.cfg \
    file://ipv6.cfg \
    file://virt.cfg \
    file://rdma.cfg \
    file://ptp.cfg \
    file://nls.cfg \
    ${@bb.utils.contains('TARGET_ARCH', 'aarch64', 'file://scmi.cfg', '', d)} \
    file://rtc.cfg \
"

do_add_dts(){
    mkdir -p ${S}/arch/${ARCH}/boot/dts/chronos/

    #cp ${WORKDIR}/<file1>.dts ${S}/arch/${ARCH}/boot/dts/chronos/
    #cp ${WORKDIR}/<file2>.dts ${S}/arch/${ARCH}/boot/dts/chronos/
}

addtask add_dts after do_patch before do_configure

COMPATIBLE_MACHINE = "^(daytona|tank)$"
