SUMMARY = "Debugging tools for chronos"

inherit packagegroup

RDEPENDS:${PN} = " \
    openssh-sftp-server \
    gdbserver \
    binutils \
    rt-tests \
    ethtool \
    tcpdump \
    strace \
    iperf \
    ldd \
"
