import mysql.connector
from http.server import HTTPServer, BaseHTTPRequestHandler
import requests
cnx = mysql.connector.connect(host="127.0.0.1",
                                user="insertUserHere",
                                passwd="insertPasswordHere",
                                database = 'insertDatabaseNameHere')
cursor = cnx.cursor()

class S(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()


    def do_GET(self):
        try:
            f = open("index.html","rb")

            self._set_headers()
            self.wfile.write(f.read())

            f.close()
            return
        except IOError:
            self.send_error(404, "File Not Found: %s" % self.path)


    def do_HEAD(self):
        self._set_headers()

    def do_POST(self):
        # Doesn't do anything with posted data
        try:
            f = open("index.html","rb")
            content_length = int(self.headers['Content-Length']) # <--- Gets the size of data
            post_data = self.rfile.read(content_length)
            s = post_data.decode()
            data_lst = s.split('&')
            user = data_lst[0].split('=')
            msg = data_lst[1].split('=')
            val = (user[1], msg[1])
            insert(val)
            self._set_headers()
            self.wfile.write(f.read())

            f.close()
            return
        except IOError:
            self.send_error(404, "Input not valid: %s" % self.path)



def run(server_class=HTTPServer, handler_class=S, port=8080):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print('Starting httpd...')
    httpd.serve_forever()

def insert(val):
    cursor.execute("INSERT INTO messages (message, user) VALUES (%s, %s)", val)
    cnx.commit()

if __name__ == "__main__":
    from sys import argv

    if len(argv) == 2:
        run(port=int(argv[1]))
    else:
        run()
