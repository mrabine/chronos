#!/bin/sh

[ -n "$(cat /proc/cmdline | grep -o "net.ifnames=0")" ] && exit 0;

OLDNAME=$MDEV

[ -d "/sys/class/net/$OLDNAME" ] || exit 0
[ -L "/sys/class/net/$OLDNAME/device" ] || exit 0

DEVICE_PATH=$(realpath /sys/class/net/$OLDNAME/device)

PCI_PATH=""
CURRENT_PATH="$DEVICE_PATH"

while [ -n "$CURRENT_PATH" ] && [ "$CURRENT_PATH" != "/sys/devices" ]; do
    BASENAME=$(basename "$CURRENT_PATH")
    if echo "$BASENAME" | grep -qE '^[0-9a-f]{4}:[0-9a-f]{2}:[0-9a-f]{2}\.[0-9]$'; then
        PCI_PATH="$CURRENT_PATH"
        break
    fi
    CURRENT_PATH=$(dirname "$CURRENT_PATH")
done

[ -z "$PCI_PATH" ] && exit 0

PCI_ID=$(basename $PCI_PATH)

BUS=$(echo $PCI_ID | cut -d: -f2)
BUS=$((10#$BUS))

SLOTFUNC=$(echo $PCI_ID | cut -d: -f3)
SLOT=$(echo $SLOTFUNC | cut -d. -f1)
SLOT=$((10#$SLOT))

NEWNAME="enp${BUS}s${SLOT}"

if [ "$OLDNAME" != "$NEWNAME" ]; then
    ip link set "$OLDNAME" down >/dev/null 2>&1 || exit 1
    ip link set "$OLDNAME" name "$NEWNAME" >/dev/null 2>&1 || exit 1
    ip link set "$NEWNAME" up >/dev/null 2>&1 || exit 1
fi

exit 0
