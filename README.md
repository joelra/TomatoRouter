TomatoRouter
============

Project to control a flashed router with Tomato from an android device.


============

This is currently a work in progress. More information will be provided as the application develops.

============
Router information to get:

router_name
wan_hwaddr
lan_ipaddr
t_model_name

uptime_s
totalram
freeram

Devices - list

============
Status-data.jsx

/*
	Tomato GUI
	Copyright (C) 2006-2010 Jonathan Zarate
	http://www.polarcloud.com/tomato/

	For use with Tomato Firmware only.
	No part of this file may be used without permission.
*/
//
nvram = {
	l2tp_get_ip: '',
	pptp_get_ip: '',
	pptp_server_ip: '',
	router_name: 'tomato2',
	wan_domain: '',
	wan_gateway: '0.0.0.0',
	wan_get_domain: '',
	wan_hostname: 'unknown',
	wan_hwaddr: '00:22:6B:79:86:AA',
	wan_ipaddr: '0.0.0.0',
	wan_netmask: '0.0.0.0',
	wan_proto: 'dhcp',
	wan_run_mtu: '1500',
	et0macaddr: '00:22:6B:79:86:A9',
	lan_proto: 'dhcp',
	lan_ipaddr: '192.168.1.1',
	dhcp_start: '100',
	dhcp_num: '50',
	dhcpd_startip: '192.168.1.100',
	dhcpd_endip: '192.168.1.149',
	lan_netmask: '255.255.255.0',
	security_mode2: 'wpa_personal',
	wl_crypto: 'aes',
	wl_mode: 'ap',
	wds_enable: '0',
	wl0_hwaddr: '00:22:6B:79:86:AB',
	wl_net_mode: 'b-only',
	wl_radio: '1',
	wl_channel: '10',
	lan_gateway: '0.0.0.0',
	wl_ssid: 'Tomato_Test',
	t_model_name: 'Linksys WRT54G/GS/GL',
	http_id: 'TID7d67eacd011acbba',
	web_mx: 'status,bwm',
	web_pb: ''};

//
//
sysinfo = {
	uptime: 2073,
	uptime_s: '0 days, 00:34:33',
	loads: [0, 0, 0],
	totalram: 14876672,
	freeram: 6377472,
	shareram: 0,
	bufferram: 1032192,
	cached: 3280896,
	totalswap: 0,
	freeswap: 0,
	totalfreeram: 6377472,
	procs: 19
};

//
wlradio = 1;

stats = { };
do {
var a, b, i;
if (typeof(last_wan_proto) == 'undefined') {
last_wan_proto = nvram.wan_proto;
}
else if (last_wan_proto != nvram.wan_proto) {
reloadPage();
}
stats.cpuload = ((sysinfo.loads[0] / 65536.0).toFixed(2) + '<small> / </small> ' +
(sysinfo.loads[1] / 65536.0).toFixed(2) + '<small> / </small>' +
(sysinfo.loads[2] / 65536.0).toFixed(2));
stats.uptime = sysinfo.uptime_s;
a = sysinfo.totalram + sysinfo.totalswap;
b = sysinfo.totalfreeram + sysinfo.freeswap;
stats.memory = scaleSize(a) + ' / ' + scaleSize(b) + ' <small>(' + (b / a * 100.0).toFixed(2) + '%)</small>';
stats.time = 'Not Available';
stats.wanup = '0' == '1';
stats.wanuptime = '-';
stats.wanlease = '0 days, 00:00:00';
//
dns = ['8.8.8.8:53'];

stats.dns = dns.join(', ');
stats.wanip = nvram.wan_ipaddr;
stats.wannetmask = nvram.wan_netmask;
stats.wangateway = nvram.wan_gateway;
switch (nvram.wan_proto) {
case 'pptp':
if (stats.wanup) {
stats.wanip = nvram.pptp_get_ip;
stats.wannetmask = '255.255.255.255';
}
else {
stats.wangateway = nvram.pptp_server_ip;
}
break;
case 'l2tp':
if (stats.wanup) {
stats.wanip = nvram.l2tp_get_ip;
stats.wannetmask = '255.255.255.255';
}
break;
default:
if (!stats.wanup) {
stats.wanip = '0.0.0.0';
stats.wannetmask = '0.0.0.0';
stats.wangateway = '0.0.0.0';
}
}
stats.wanstatus = 'Renewing...';
if (stats.wanstatus != 'Connected') stats.wanstatus = '<b>' + stats.wanstatus + '</b>';
a = i = '10' * 1;
if (i < 0) i = -i;
if ((i >= 1) && (i <= 14)) {
stats.channel = '<a href="tools-survey.asp">' + i + ' - ' + ghz[i - 1] + ' <small>GHz</small></a>'
if (a < 0) stats.channel += ' <small>(scanning...)</small>';
}
else if (i == 0) {
stats.channel = 'Auto';
}
else {
stats.channel = '-';
}
wlcrssi = wlnoise = stats.qual = '';
isClient = ((nvram.wl_mode == 'wet') || (nvram.wl_mode == 'sta'));
if (wlradio) {
if (isClient) {
//
wlnoise = -99;

//
wlcrssi = 0;

if (wlcrssi == 0) a = 0;
else a = MAX(wlcrssi - wlnoise, 0);
stats.qual = a + ' <img src="bar' + MIN(MAX(Math.floor(a / 10), 1), 6) + '.gif">';
}
wlnoise += ' <small>dBm</small>';
wlcrssi += ' <small>dBm</small>';
}
} while (0);
