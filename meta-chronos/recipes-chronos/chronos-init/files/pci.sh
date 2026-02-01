#!/bin/sh

[ -n "$(cat /proc/cmdline | grep -o "net.ifnames=0")" ] && exit 0;

OLDNAME=$MDEV

[ -d "/sys/class/net/$OLDNAME" ] || exit 0
[ -L "/sys/class/net/$OLDNAME/device" ] || exit 0

DEVICEPATH=$(realpath /sys/class/net/$OLDNAME/device)

PCIPATH=""
CURRENTPATH="$DEVICEPATH"

while [ -n "$CURRENTPATH" ] && [ "$CURRENTPATH" != "/sys/devices" ]; do
    BASENAME=$(basename "$CURRENTPATH")
    if echo "$BASENAME" | grep -qE '^[0-9a-f]{4}:[0-9a-f]{2}:[0-9a-f]{2}\.[0-9]$'; then
        PCIPATH="$CURRENTPATH"
        break
    fi
    CURRENTPATH=$(dirname "$CURRENTPATH")
done

[ -z "$PCIPATH" ] && exit 0

PCIID=$(basename $PCIPATH)

BUS=$(echo $PCIID | cut -d: -f2)
BUS=$((10#$BUS))

SLOTFUNC=$(echo $PCIID | cut -d: -f3)
SLOT=$(echo $SLOTFUNC | cut -d. -f1)
SLOT=$((10#$SLOT))

NEWNAME="enp${BUS}s${SLOT}"

if [ "$OLDNAME" != "$NEWNAME" ]; then
    WASUP=0
    if ip link show "$OLDNAME" 2>/dev/null | grep -q "state UP"; then
        WASUP=1
    fi

    ip link set "$OLDNAME" down >/dev/null 2>&1 || exit 1
    ip link set "$OLDNAME" name "$NEWNAME" >/dev/null 2>&1 || exit 1

    if [ "$WASUP" -eq 1 ]; then
        ip link set "$NEWNAME" up >/dev/null 2>&1 || exit 1
    fi
fi

exit 0
