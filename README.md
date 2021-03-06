Prerequisite (install it first):
* https://github.com/zeromq/jzmq
* https://github.com/zeromq/libzmq

Used: 
* jzmq 3.1.1-SNAPSHOT
* zeroMQ 4.2.3
* OpenJDK Runtime Environment (Zulu 8.23.0.3-linux64) (build 1.8.0_144-b01)
* Linux 9f1e8b40de13 4.4.0-97-generic #120-Ubuntu SMP Tue Sep 19 17:28:18 UTC 2017 x86_64 x86_64 x86_64 GNU/Linux
* Host machine _(in my case)_:
    * Docker version 17.05.0-ce, build 89658be
    * Docker-compose version 1.9.0, build 2585387
    * CPU Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz
    * SSD OCZ-VECTOR150
    * DDR3 Synchronous 1600 MHz Rockwell

```bash
 mvn clean package && docker-compose up
```
```
HOST >>

IPC (Host)
Connecting to ipc:///tmp/zmq/zzx.ipc
Launch consumer...
RPC calls:        100000
Message size:        10240 bytes

Running...
Messages send/received:    100000/100000
Bytes send/received:       1024000000/1024000000
Transmission time:         7509ms
Deviation:                 975ms
Run complete.

TCP (Host loopback)
Connecting to tcp://localhost:5559
Launch consumer...
RPC calls:                 100000
Message size:              10240 bytes

Running...
Messages send/received:    100000/100000
Bytes send/received:       1024000000/1024000000
Transmission time:         10268ms
Deviation:                 1021ms
Run complete.


DOCKER >>

TCP (Docker-Docker inner network)
provider_1  | Listening on tcp://*:5599
provider_1  | Launch provider...
consumer_1  | Connecting to tcp://provider:5599
consumer_1  | Using message size:       10240
consumer_1  | Request count:            100000
consumer_1  |
consumer_1  | Launch consumer...
consumer_1  | RPC calls:                100000
consumer_1  | Message size:             10240 bytes
consumer_1  |
consumer_1  | Running...
consumer_1  | Messages send/received:   100000/100000
consumer_1  | Bytes send/received:      1024000000/1024000000
consumer_1  | Transmission time:        12322ms
consumer_1  | Deviation:                825ms
consumer_1  | Run complete.


IPC (Docker-Docker common volume)
provider_1     | Listening on ipc:///tmp/zmq/z.ipc
provider_1     | Launch provider...
consumer_1  | Connecting to ipc:///tmp/zmq/z.ipc
consumer_1  | Using message size: 10240
consumer_1  |  Request count: 100000
consumer_1  |
consumer_1  | Launch consumer...
consumer_1  | RPC calls:                100000
consumer_1  | Message size:             10240 bytes
consumer_1  |
consumer_1  | Running...
consumer_1  | Messages send/received:   100000/100000
consumer_1  | Bytes send/received:      1024000000/1024000000
consumer_1  | Transmission time:        7378ms
consumer_1  | Deviation:                845ms
consumer_1  | Run complete.
```
--------------
VM OPTIONS:
 * -ea -Djava.library.path=/usr/local/lib

ENV: 
* CONSUMER_CONNECT_ADDR=ipc:///tmp/zmq/zzx.ipc 
* PROVIDER_LISTEN_ADDR=ipc:///tmp/zmq/zzx.ipc