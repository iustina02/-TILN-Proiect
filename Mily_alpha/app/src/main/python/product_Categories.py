import rowordnet

def test(arg1):
    wn =  rowordnet.RoWordNet()

    print("Arg: " + arg1);
    # accented_string = u'âățșî'
    # unaccednted_string = unidecode.unidecode(accented_string)
    # print(unaccednted_string)

    list_antent = ["str", "nr.", "jud.", "mun.", "cod", "identificare","fiscala"]
    list_preturi = ["buc", "x", "1.000","c","b","t/a d","ocu/","lel","bucx"]

    argSplit = arg1.split("\n")


    list_cuv_schimba = ["branza", "smantana","mar", "para", "spaghetti"]
    list_cuv_diacritice = ["brânză","smântână","măr", "pară", "spaghete"]
    Categorii = ["lactate", "ulei", "fruct", "legumă","rădăcină","cereală","făină","condiment","mirodenie","alcool","fasole","tubercul","paste","sos","carne"]
    Non_Products = ["sls", "kaufland", "str.", "nr.", "jud.", "mun.", "cod","totall",
                    "identificare", "fiscala", "total", "lei", "tva", "tua", "punga",
                    "cash", "kgx", "mn.","romania","anbalaj", "ambalaj","cocd", "tdentificare", "pentru", "prosop", "glade"]

    list_carne = ["pui", "tocata", "os","pulpe"]
    list_faina_gris_malai = ["faina","gris","malai"]
    list_alcool = ["ciuc","radler","ursus","salitos"]
    list_suc = ["cola"]
    list_gustari = ["lay s","chio","lays","chips","krax"]

    Produs_Catego = {}

    for produ in argSplit:
        argSplit2 = produ.split(" ")

        pass_this = False;
        for non_product in argSplit2:
            if non_product.lower() in Non_Products:
                pass_this = True
            if(non_product.lower() == "tva"):
                return "end"
        if pass_this == False:
            for wrd in argSplit2:
                print("Word in produs: " + wrd)
                if wrd.lower() in list_antent or wrd.lower() in list_preturi:
                    continue
                if wrd.isnumeric() or hasNumbers(wrd) is True or len(wrd)<=2:
                    continue
                else:
                    for cuv in list_cuv_schimba:
                        if wrd.lower() == cuv:
                            wrd = list_cuv_diacritice[list_cuv_schimba.index(cuv)]
                            print ("Diacritice: " + wrd)

                    if wrd.lower() in list_carne:
                        Produs_Catego[produ] = "carne,"
                        break
                    if wrd.lower() in list_faina_gris_malai:
                        Produs_Catego[produ] = "faina; gris; malai,"
                        break
                    if wrd.lower() in list_alcool:
                        Produs_Catego[produ] = "alcool,"
                        break
                    if wrd.lower() in list_suc:
                        Produs_Catego[produ] = "suc,"
                        break
                    if wrd.lower() in list_gustari:
                        Produs_Catego[produ] = "gustari,"
                        break


                    synset_ids = wn.synsets(literal=wrd.lower())
                    list_literals = []
                    if synset_ids:
                        for synset_id in synset_ids:
                            list_hypernym = wn.list_hypernyms(synset_id)
                            if list_hypernym:
                                for hypernym in list_hypernym:
                                    list_literals = wn.print_synset(hypernym)
                        if list_literals:
                            new_list_literals = ""
                            # print("Literals:  \n")
                            for literals in list_literals:
                                if literals in Categorii and literals not in new_list_literals:
                                    new_list_literals += literals + ";"
                            if new_list_literals:
                                Produs_Catego[produ] = new_list_literals + ","
                    else:
                        # print ("Nu a fost gasita o categorie !")
                        Produs_Catego[produ] = "Nici o categorie gasita,"

    if Produs_Catego:
        print(Produs_Catego)
        return Produs_Catego
    else:
        return "Detalii"

def hasNumbers(inputString):
    return any(char.isdigit() for char in inputString)
