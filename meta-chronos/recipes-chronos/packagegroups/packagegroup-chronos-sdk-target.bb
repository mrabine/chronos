SUMMARY = "chronos target dependencies required in SDK"

inherit packagegroup

RDEPENDS:${PN} = " \
    libstdc++-dev libstdc++-staticdev \
    googletest-staticdev \
    join-dev \
"
