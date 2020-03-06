# Streaming Gateway for nginx-rtmp
## Usage
### Configure gateway
1. Build the project ```./mvnw clean package``` or download a release from releases tab
2. Copy the jar to the destination server
3. Create an ```application.properties``` file. An example file is in the repository
4. Add at least one user to the properties file
5. Launch the software with ```java -jar downloaded_name.jar```

### Configure nginx-rtmp
The example configuration file takes the input data in the ingest application 
and then publishes it to the live application. The ```ffmpeg``` executions enable different stream qualities.
On ```/live/name```, the original stream quality is available, the other options transcode to lower resolutions or
reduced frame rates in order to reduce data throughput. The ```on_publish``` URL needs to match the port and IP address
specified in ```application.properties```

```nginx
rtmp {
        server {
                listen [::]:1935 ipv6only=off;
                chunk_size 8192;

                application ingest {
                        live on;
                        meta copy;
                        exec ffmpeg -i rtmp://localhost/ingest/$name -c:v copy -c:a copy -f flv rtmp://localhost/live/$name;
                        exec ffmpeg -i rtmp://localhost/ingest/$name -c:v libx264 -c:a copy -s 1920x1080 -filter:v fps=fps=30 -f flv rtmp://localhost/live/1080p_$name;
                        exec ffmpeg -i rtmp://localhost/ingest/$name -c:v libx264 -c:a copy -s 1280x720 -filter:v fps=fps=30 -f flv rtmp://localhost/live/720p_$name;
                        on_publish http://127.0.0.1:31337/auth;
                        allow play 127.0.0.1;
                        allow play ::1;
                        deny play all;
                }

                application live {
                        live on;
                        meta copy;
                        allow publish 127.0.0.1;
                        allow publish ::1;
                        deny publish all;
                        wait_key on;
                        wait_video on;
                }
        }
}
```
Remember to restart nginx after making configuration changes.

### Configure your streaming software
In your streaming software, set the server to ```rtmp://yourserver/ingest/```, and the passkey to
```username?key=passkey```, where ```username``` and ```passkey``` are the ones you defined in
```application.properties```. Now, you should be able to start streaming. If your streaming software
produces an error, make sure you correctly set the stream key (```name``` is *not* part of the server,
it is part of your stream key). The gateway logs all authentication attempts, so you can verify whether
your attempts actually made it to the server.

### Watch your streams
With the example config above, the URLs to watch the streams are
1. ```rtmp://servername/live/name``` for the original data
2. ```rtmp://servername/live/1080p_name``` remastered to 1080p30
3. ```rtmp://servername/live/720p_name``` remastered to 720p30
