# The order of packages is significant as there are dependencies between
# the packages. Typically generated namespaces are used by other packages.
PACKAGES = l3vpn

# The create-network argument to ncs-netsim
NETWORK = create-network cisco-ios    6 ce \
		  create-network cisco-iosxr  3 pe \
#		  create-network juniper-junos 1 pe \

NETSIM_DIR = netsim

all: build-all $(NETSIM_DIR)

build-all:
	for i in $(PACKAGES); do \
		$(MAKE) -C packages/$${i}/src all || exit 1; \
	done

#Patch to add ios-stats
packages/cisco-ios:
	ln -s $(NCS_DIR)/packages/neds/cisco-ios packages/cisco-ios

packages/cisco-iosxr:
	ln -s $(NCS_DIR)/packages/neds/cisco-iosxr packages/cisco-iosxr

$(NETSIM_DIR): packages/cisco-ios packages/cisco-iosxr
	ncs-netsim --dir netsim $(NETWORK)
	cp initial_data/ios.xml netsim/ce/ce0/cdb
	cp initial_data/ios.xml netsim/ce/ce1/cdb
	cp initial_data/ios.xml netsim/ce/ce2/cdb
	cp initial_data/ios.xml netsim/ce/ce3/cdb
	cp initial_data/ios.xml netsim/ce/ce4/cdb
	cp initial_data/ios.xml netsim/ce/ce5/cdb
	cp initial_data/iosxr.xml netsim/pe/pe0/cdb
	cp initial_data/iosxr.xml netsim/pe/pe1/cdb
	cp initial_data/iosxr.xml netsim/pe/pe2/cdb
	ncs-netsim ncs-xml-init > ncs-cdb/netsim_devices_init.xml

clean:
	for i in $(PACKAGES); do \
		$(MAKE) -C packages/$${i}/src clean || exit 1; \
	done
	rm -rf netsim running.DB logs/* state/* ncs-cdb/*.cdb *.trace
	rm -f packages/cisco-ios
	rm -f packages/juniper-junos
	rm -f packages/cisco-iosxr
	rm -rf bin
	rm -rf ncs-cdb/*.xml

start:
	ncs-netsim start
	ncs

stop:
	-ncs-netsim stop
	-ncs --stop

reset:
	ncs-setup --reset

cli:
	ncs_cli -u admin
