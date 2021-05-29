#!/bin/bash

SERVICE_PORT_LISTEN=0

refreshServicePortListen(){
    SERVICE_PORT_LISTEN=$(netstat -anp |grep nginx | grep 80 | grep LISTEN | wc -l)
}

startService(){
    /usr/local/nginx/sbin/nginx
}

refreshServicePortListen
if [ ${SERVICE_PORT_LISTEN} -lt 1 ]; then
    startService
    sleep 3
    refreshServicePortListen
    if [ ${SERVICE_PORT_LISTEN} -lt 1 ]; then 
        killall keepalived
    fi
fi

