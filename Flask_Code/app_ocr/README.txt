## Usage : 

. The process of extracting information from a digital copy of invoice can be a tricky task. There are various tools that are available in the market that can be used to perform this task
 pdf2image (for converting PDF to images), OpenCV (for Image pre-processing) , openOCR for OCR along with Python ,etc ...
.This project will allow the banks to get the components of the balance sheet  (bilan financiére)  provided by the companies in the end of every year .
.This code work only for french balance (bilan) 

## Installation : 

***Project dependencies :*** 
	.CUDA
	.Numpy
	.PyTorch
	.EasyOCR 
	.OpenCV 
	.Flask
	.Build_in Python libraries :  difflib

.You should have python 3.10.5 installed in your machine and the pip package installer to help you to install the latest stable versions of the dependencies for this project .

. PyTorch :
 	.with support of CUDDA  :   pip3 install torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu113  (it's recommended to use GPU because he is so much faster)
	.CPU version   :  pip3 install torch torchvision torchaudio
.EasyOCR : which depend to pytorch to work  
	.pip install easyocr  
.Numpy
	.pip install numpy 
. pdf2image
	.pip install pdf2image  
		Note  :  pdf2image uses Poppler which is a PDF rendering library based on the xpdf-3.0 code base 	
			and will not work without it. Please refer to the below link  for downloading and installation instructions for Poppler : 
			.link : https://stackoverflow.com/questions/18381713/how-to-install-poppler-on-windows
.OpenCV : 
	.pip install opencv-python
.Flask : 
	.pip install flask

##Run :
	.if you already  installed all the dependencies then you  can  run the script 'app.py'  , command > python   /folder/path/app.py 
	the API now is running and you can consume it with the request : method[POST] : http://127.0.0.1:5000/uploadFile , Query Params : the balance sheet (pdf version) 
	and it will return to you json file containing the data extracted from the pdf file .
	

##Notes :
	
	Note :  Before marking regions make sure that you have preprocessed the image 
		for improving its quality (DPI ≥ 350, Skewness, Sharpness and Brightness should be adjusted, Thresholding etc.)
		for that you have to adjust image_quality variable in utils.py (recommended quality of DPI = 350) ,
		and the adjusment depend on the ressources of your machine  (basicly the GPU and the CPU).