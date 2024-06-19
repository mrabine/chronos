SUMMARY = "Debugging tools for chronos"

inherit packagegroup

RDEPENDS:${PN} = " \
    gdbserver \
    openssh-sftp-server \
    strace \
"
