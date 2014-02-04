(ns manager.nginx)

(def host-redirect
  { "bibdata.dk" "/notes/biblioteksting"
    "blaagaard-kdas.solsort.com" "/blaagaard-kdas"
    "combigame.com combigame.solsort.com" "/speeding"
    "dragimation.solsort.com" "/dragimation"
    "html5.solsort.com" "/notes/html5"
    "jurapp.dk jurapp.com jurapp.solsort.com" "/jurapp"
    "lightscript.solsort.com" "/lightscript"
    "rasmuserik.com" "/rasmuserik"
    "rasmuserik.dk" "/rasmuserik"
    "rasmuserik.net" "/rasmuserik"
    "rasmuserik.solsort.com" "/rasmuserik"
    "skolevangen.solsort.com" "/skolevangen"
    "solsort.dk" "/"
    "speeding.solsort.com" "/speeding"
    "theodorelias.solsort.com" "/theodorelias"
    "www.solsort.com" "/"
    "www.solsort.dk " "/"})

(def url-redirect
  { "/2012/cphjs/$" "/slides/osd2012-javascript"
    "/2013/berlincompiler$" "/slides/berlincompiler2013-yolan"
    "/apps$" "/"
    "/apps/blobshot$" "http://blobshot.solsort.com"
    "/apps/massdrive$" "/massdrive"
    "/Back_online$" "/notes/Back_online"
    "/CombiGame$" "/combigame"
    "/dkcities.*$" "/danske-byer"
    "/dkcities$" "/danske-byer"
    "/EuroCards$" "/notes/EuroCards"
    "/Fototips$" "/notes/Fototips"
    "/home$" "/"
    "/html5/cnug-2013-slides.html$" "/slides/cnug2013-html5"
    "/html5/cnug2013-slides.html$" "/slides/cnug2013-html5"
    "/html5$" "/notes/html5"
    "/html5/osd2013-slides.html$" "/slides/osd2013-html5"
    "/html5/slides.html$" "/notes/html5"
    "/jurapp$" "http://jurapp.solsort.com"
    "/MiniLD_36_Contrasts$" "/notes/MiniLD-36-contrasts"
    "/News$" "/"
    "/notes/Back_online$" "/"
    "/notes/CombiGame.html$" "/combigame"
    "/notes/MiniLD36_Contrasts$" "/notes/MiniLD-36-contrasts"
    "/notes/News$" "/"
    "/notes/Opskrift_Kartoffel_ggekage$" "/notes/Opskrift_Kartoffelaeggekage"
    "/notes/TED-talks$" "/links"
    "/notes/Tekststruktur_for_rapporter$" "/notes/skrivetips"
    "/notes/Toastmaster_notes_for_evaluating$" "/notes/presentation-evaluation"
    "/notes/Tommelfingerregler_for_skrivning$" "/notes/skrivetips"
    "/Opskrift_Hummus$" "/notes/Opskrift_Hummus"
    "/Opskrift_Karry_Dolmers$" "/notes/Opskrift_Karry_Dolmers"
    "/Opskrift_Kartoffelaeggekage$" "/notes/Opskrift_Kartoffelaeggekage"
    "/Opskrift_Ovnbagt_laks_med_kartofler_og_persillesovs$" "/notes/Opskrift_Ovnbagt_laks_med_kartofler_og_persillesovs"
    "/osd2012/$" "/slides/osd2013-javascript"
    "/presentation-evaluation-notes$" "/notes/presentation-evaluation"
    "/pricing-scale$" "/notes/pricing-scale"
    "/privacy$" "/notes/privacy"
    "/productivity-hacks$" "/notes/productivity-hacks"
    "/rasmuserik/(.*)$" "/$1"
    "/skrivetips$" "/notes/skrivetips"
    "/solsort$" "/"
    "/status.*$" "/"
    "/status-2013-april-26$" "/"
    "/status-2013-may-10$" "/"
    "/status-2013-may-24$" "/"
    "/summerhacks/$" "/slides/cphjs2013-summer-hacks"
    "/summerhacks$" "/slides/cphjs2013-summer-hacks"
    "/tango$" "/notes/tango"
    "/Tekststruktur_for_rapporter$" "/notes/skrivetips"
    "/Toastmaster_notes_for_evaluating$" "/notes/presentation-evaluation"
    "/Tommelfingerregler_for_skrivning$" "/notes/skrivetips"
    "/tsar_tnoc/*$" "/tsartnoc"
    "/writings$" "/"})

(defn nginxConf [cfg]
  { :user "www-data"
    :worker_processes "1"
    :pid "/var/run/nginx.pid"
    :events { :worker_connections "768"}
    :http
    (concat
      { :client_max_body_size "100M"
        :sendfile "on"
        :tcp_nopush "on"
        :tcp_nodelay "on"
        :keepalive_timeout "65"
        :types_hash_max_size "2048"

        :include "/etc/nginx/mine.types"
        :default_type "application/octet-stream"

        :error_log (str (cfg :root) "/log/error.log")
        :access_log (str (cfg :root) "/log/access.log")

        :gzip "on"
        :gzip_disable "\"msie6\""}
      (map
        (fn [[key, val]]
          [:server [:server_name key
                    :rewrite (str "^ http://solsort.com" val " permanent")]])
        url-redirect)
      [[ :server
         [ :server_name "~^(?<domain>.*)$"
           :root (str (cfg :root) "/www")
           :TODO
          ]
       ]
       [ :server
         { :listen "443"
           :ssl "on"
           :ssl_certificate (str (cfg :root) "ssl-keys/server.crt")
           :ssl_certificate_key (str (cfg :root) "ssl-keys/server.key")
           :server_name "ssl.solsort.com solsort.com localhost"
           :location "/ {
              proxy_set_header X-Real-IP $remote_addr;
              proxy_pass http://127.0.0.1:80;}" }]])})


(nginxConf {:root "/home/server"})

(defn generate []
  (str
   "server {
     root /home/server/public_html;
     index index.html;
   }"))