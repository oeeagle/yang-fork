all:	fxs

# Include standard NCS examples build definitions and rules
include $(NCS_DIR)/src/ncs/build/include.ncs.mk

YANGPATH= --yangpath /opt/ncs/current/packages/neds/f5-bigip/src/ncsc-out/modules/yang \
 --yangpath /opt/ncs/current/packages/neds/juniper-junos/src/ncsc-out/modules/yang \
 --yangpath /opt/ncs/current/packages/neds/cisco-nx/src/ncsc-out/modules/yang \
 --yangpath /opt/ncs/current/packages/neds/cisco-ios/src/ncsc-out/modules/yang \
 --yangpath /opt/ncs/current/packages/neds/cisco-iosxr/src/ncsc-out/modules/yang \
 --yangpath /opt/ncs/current/packages/neds/a10-acos/src/ncsc-out/modules/yang \

SRC = $(wildcard yang/*.yang)
FXS = $(SRC:yang/%.yang=../load-dir/%.fxs)

fxs:	$(FXS)

../load-dir/%.fxs:	yang/%.yang
	$(NCSC)  `ls $*-ann.yang  > /dev/null 2>&1 && echo "-a $*-ann.yang"` \
             $(YANGPATH) -c -o $@ $<
clean:
	rm -rf  ../load-dir/*

