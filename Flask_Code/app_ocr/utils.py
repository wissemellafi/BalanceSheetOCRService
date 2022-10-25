UPLOAD_FOLDER = 'upload/pdfs'
ALLOWED_EXTENSIONS = {'pdf'}
FILE_PATH = 'upload/pdfs/bilan.pdf'
image_quality = 150
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
