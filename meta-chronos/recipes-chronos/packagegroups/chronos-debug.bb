SUMMARY = "Chronos debugging tools"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = " \
    openssh-sftp-server \
    gdbserver \
    binutils \
    rt-tests \
    perftest \
    ethtool \
    tcpdump \
    strace \
    iperf3 \
    ldd \
"
