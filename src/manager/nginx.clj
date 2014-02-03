(ns manager.nginx)

(defn generate []
  (str
   "server {
     root /home/server/public_html;
     index index.html;
   }"))