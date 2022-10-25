from pipeline import bilanOCR 
import os
from flask import Flask, request , jsonify
from utils import allowed_file , FILE_PATH ,UPLOAD_FOLDER


app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/uploadFile', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        # check if the post request has the file part
        if 'file' not in request.files:
            return'No file part'
        file = request.files['file']
        # If the user does not select a file, the browser submits an
        # empty file without a filename.
        if file.filename == '':
            return'No selected file' 
        if file and allowed_file(file.filename):
            filename = "bilan.pdf"
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            return jsonify(bilanOCR(FILE_PATH))
    return "can't upload file" 
if os.path.exists(FILE_PATH):
  os.remove(FILE_PATH)
else:
  print("The file does not exist")
app.run(port= 5000)