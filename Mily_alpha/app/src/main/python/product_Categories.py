import rowordnet
# import unidecode

def tests(arg):
    return ("\n Text Block: " + arg +"\n");

def test(arg1):
    wn =  rowordnet.RoWordNet()

    # accented_string = u'âățșî'
    # unaccednted_string = unidecode.unidecode(accented_string)
    # print(unaccednted_string)



    argSplit = arg1.split("\n")

    list_cuv_schimba = ["branza", "smantana","mar", "para", "spaghetti"]
    list_cuv_diacritice = ["brânză","smântână","măr", "pară", "spaghete"]
    Categorii = ["altceva","lactate", "ulei", "fruct", "legumă","rădăcină","cereală","făină","condiment","mirodenie","alcool","fasole","tubercul","paste","sos","carne"]

    Produs_Catego = {}

    for produ in argSplit:
        argSplit2 = produ.split(" ")
        # print ("Produs: " + produ)
        Non_Products = ["sls", "kaufland", "str.", "nr.", "jud.", "mun.", "cod",
                        "identificare", "fiscala", "total", "lei", "tva", "tua", "punga",
                        "cash", "kgx", "mn.","romania","anbalaj", "ambalaj","cocd", "tdentificare", "pentru", "prosop", "glade"]

        pass_this = False;
        for non_product in argSplit2:
            if non_product.lower() in Non_Products:
                pass_this = True
        if pass_this == False:
            for wrd in argSplit2:
                if wrd.isnumeric() or hasNumbers(wrd) is True or "BUC" in wrd or wrd == "x" or wrd == "X" or len(wrd)<=2:
                    continue
                else:
                    # print("Word in produs: " + wrd)
                    for cuv in list_cuv_schimba:
                        if wrd.lower() == cuv:
                            wrd = list_cuv_diacritice[list_cuv_schimba.index(cuv)]
                            print ("Diacritice: " + wrd)

                    list_carne = ["pui", "tocata", "os","pulpe"]
                    list_faina_gris_malai = ["faina","gris","malai"]
                    list_alcool = ["ciuc","radler","ursus","salitos"]
                    list_suc = ["cola"]

                    if wrd.lower() in list_carne:
                        Produs_Catego[produ] = "carne,"
                        continue
                    if wrd.lower() in list_faina_gris_malai:
                        Produs_Catego[produ] = "faina; gris; malai,"
                        continue
                    if wrd.lower() in list_alcool:
                        Produs_Catego[produ] = "alcool,"
                        continue
                    if wrd.lower() in list_suc:
                        Produs_Catego[produ] = "suc,"
                        continue


                    synset_ids = wn.synsets(literal=wrd.lower())
                    list_literals = []
                    if synset_ids:
                        for synset_id in synset_ids:
                            list_hypernym = wn.list_hypernyms(synset_id)
                            if list_hypernym:
                                for hypernym in list_hypernym:
                                    for_list = wn.print_synset(hypernym)
                                    if for_list:
                                        if len(for_list) > 1:
                                            for hep in for_list:
                                                list_literals.append(hep)
                                        elif len(for_list) == 1:
                                            list_literals.append(for_list[0])
                        if list_literals:
                            new_list_literals = ""
                            # print("Literals:  \n")
                            for literals in list_literals:
                                for categ in Categorii:
                                    if literals == categ:
                                        if literals not in new_list_literals:
                                            new_list_literals += literals + ";"
                            if new_list_literals:
                                Produs_Catego[produ] = new_list_literals + ","
                    # if produ not in Produs_Catego:
                    #     Produs_Catego[produ] = "Adauga categorie..,"

    if Produs_Catego:
        print(Produs_Catego)
        return Produs_Catego
    else:
        return "Detalii"

def hasNumbers(inputString):
    return any(char.isdigit() for char in inputString)
