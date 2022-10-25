from difflib import SequenceMatcher

#Function to calculate similarity between strings
def similarity(a,b):
    if a is None or b is None :
        return -1
    else :
        s = SequenceMatcher(None, a.upper(), b.upper())
        return s.ratio()

def extractDigits(inp_str) : 
    if (isinstance(inp_str,int) or isinstance(inp_str,float)) : 
        return inp_str
    if (inp_str == '' ) :
        return 0
    num = ""
    for c in inp_str:
        if c.isdigit():
            num = num + c
    if inp_str[0] == '-' :
        return '-' + num
    if num == '' :
        return 0
    return num

#Function to find the value of an component from the bilan 
def findValueAndAmortissements(term,text):
    count=0
    max=0
    for token in text :
        count = similarity(token.upper(), term.upper())
        if count > max :
            max=count
            index = text.index(token)
    if max <= 0.6 :
        return 0,0
    if (extractDigits(text[index+1]) == '' ):
        return 0,0
    elif (similarity(text[index+3],'amortissements') >= 0.5 or similarity(text[index+3],'provisions')>= 0.5) : 
        return text[index+1] , text[index+4]
    else :
        return text[index+1] , 0


def findValuewithoutAmortissements(term,text):
    count=0
    max=0
    for token in text :
        count = similarity(token.upper(), term.upper())
        if count > max :
            max=count
            index = text.index(token)
    if max <= 0.6 :
        return 0
    if (extractDigits(text[index+1]) == '' ):
        return 0
    else :
        return text[index+1]



#removing index column from the image because we don't need it 
def findNotes(results):
    i=0
    try :
        for token in results :
            if(similarity('notes'.upper(),token[1].upper()) > i ) :
                i = similarity('notes'.upper(),token[1].upper())
                index = token[0]
            else : 
                pass 
    except :
        pass
    return index

def extractValues(OCR_Results) :
    text = []
    for res in OCR_Results :
        text.append(res[1])
    return text