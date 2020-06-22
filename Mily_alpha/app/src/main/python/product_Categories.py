import rowordnet

def ro_word_net(word):
    word = word
    Categorii = ["lactate", "ulei", "fruct", "legumă","rădăcină","cereală","făină","condiment","mirodenie","alcool","fasole","tubercul","paste","sos","carne"]

    categorie_finala = "none"

    RoWornNet = rowordnet.RoWordNet()

    synset_ids = RoWornNet.synsets(literal=word)

    if(synset_ids):
        for synset_id in synset_ids:
            list_id_hypernims = []

            outbound_relations = RoWornNet.outbound_relations(synset_id)
            print("\t  Outbound relations: ")
            if(outbound_relations):
                for out_synset_id, relation in outbound_relations:
                    if relation == "hypernym" :
                        list_id_hypernims.append(out_synset_id);
                    if relation == "near_eng_derivat" or relation == "hyponym":
                        break
                    # print("\t\t  {} - {}".format(out_synset_id, relation))
                    if list_id_hypernims:
                        # print("hypernim in produs: " + word)
                        for hypernym_id in list_id_hypernims:
                            synset = RoWornNet.synset(hypernym_id)
                            for litaral in synset.literals:
                                print("\t  Literals:" + litaral)
                                if litaral in Categorii:
                                    return litaral
    return categorie_finala

def hasNumbers(inputString):
    return any(char.isdigit() for char in inputString)

def test(arg1):
    print("Arg: " + arg1);
    # accented_string = u'âățșî'
    # unaccednted_string = unidecode.unidecode(accented_string)
    # print(unaccednted_string)

    list_antent = ["str", "nr.", "jud.", "mun.", "cod", "identificare","fiscala", "lel", "mun","klc","kalfland","romania","s.c.s","bld.","nr","mn.","ideniificare","fiscala:"]
    list_preturi = ["buc", "x", "c","b","t/a d","ocu/","lel","bucx","le","e","total","lei"]

    argSplit = arg1.split("\n")

    list_cuvinte_plural_after = ["maslina","banana","ciuperca","mar","capsuna","castravete","lamaie","morcov"]

    list_cuvinte_plural_before = ["masline","banane","ciuperci","mere","capsuni","castraveti","lamai","morcovi"]

    list_cuvinte_diacritice_after = ["măslină","muștar","mălai","lămâie","banană","ciupercă","măr","căpșună","smântână","brânză","cârnaț",
                                     "ceapă", "para", "spaghete","găină"]
    list_cuvinte_diacritice_before = ["maslina","mustar","malai","lamaie","banana","ciuperca","mar","capsuna","smantana","branza","carnat",
                                      "ceapa", "pară", "spaghetti","pui"]


    Non_Products_1 = ["sls", "kaufland", "str.", "nr.", "jud.", "mun.", "cod","totall",
                    "identificare", "fiscala", "total", "lei", "tva", "tua", "punga",
                    "cash", "kgx", "mn.","romania","anbalaj", "ambalaj","cocd", "tdentificare", "pentru", "prosop", "glade"]

    list_non_alimentare = ["seni","malboro","hartie","punga","deterg","sampon","seminte","serv","umede","propos","sosete","colanti","tricou",
                           "rexona","tigae","burete","spalator","metal","scobitori","lemn","bref","wc","sapun","balsam","plasturi","parbriz","orbit",
                           "baterii","lenor","sandale","dero","menaj","cif","crema","unghii","det","colgate","lavete","betisoare","caini",
                           "propper","always","bureti","pisici","domestos","geam","pulverizator","filtru","solutie","gel","sacosa","dus",
                           "samp","periuta","dinti","dizolvant","absorbate","odor","rufe","vopsea","dres","masa","dezumidificator","batiste"]


    list_branduri_lactate = ["zuzu","ladorna","danone","albalact","hochland","telemea","sana"]
    list_branduri_cereale = ["pambac","grania","bor","bur","fidelini","paste","fuslini","faina","barilla","fusili","farfalle","franzela","painea","paine","impletita"]
    list_branduri_carne = ["fox","caroli","k-pur"]
    list_branduri_condimente = ["fuchs","kamis","dr.","oetker","cosmin","oetk","diamant","diam","delikat"]
    list_branduri_gustari = ["red","bull","savia","suc","chio","lay","chips","muesli","star","krax","frutti","gusto","jacobs","seminte","nesquik",
                             "borsec","milka","popcorn","nutline","schogetten","ciocolata","biscuiti","tusnad","magura","kinder","strongbow",
                             "fanta","cozonac","nutella","espresso","jacobs","coca","cola","alka","haribo","bomboane"]
    list_branduri_acool = ["bergenbier","bere","ciuc","radler","ursus","salitos","timisoreana","unirea","zarea","angelli"]

    list_carne = ["tocata","carne","ceafa","vita","ton","icre","aripi","pulpe","piept","ou","oua","mici","kaizer","pate","muschi","sardine","os"]
    list_condimente = ["drojdie","pasta","zahar","ulei","esenta","ketchup","condimente","marar","miere","droudie"]
    list_legume = ["rosii","ardei","champignon","varza","telina","fasole","castravet","brocoli","morcovi"]
    list_fructe = ["fructe","avocado-ready-to-eat","banane/","portocale","mandarine"]

    Produs_Catego = {}

    for produ in argSplit:
        argSplit2 = produ.split(" ")

        pass_this = False;
        for non_product in argSplit2:
            if non_product.lower() in Non_Products_1 or non_product.lower() in list_preturi or non_product.lower() in list_non_alimentare:
                pass_this = True
            if(non_product.lower() == "tva"):
                return "end"
        if pass_this == False:
            for wrd in argSplit2:
                print("Word in produs: " + wrd)
                wrd = wrd.lower()
                if wrd in list_antent or wrd in list_preturi:
                    continue
                if wrd.isnumeric() or hasNumbers(wrd) is True or len(wrd)<=2:
                    continue
                else:

                    for cuv in list_cuvinte_plural_before:
                        if wrd == cuv:
                            wrd = list_cuvinte_plural_after[list_cuvinte_plural_before.index(cuv)]
                            print ("Singular: " + wrd)

                    for cuv in list_cuvinte_diacritice_before:
                        if wrd == cuv:
                            wrd = list_cuvinte_diacritice_after[list_cuvinte_diacritice_before.index(cuv)]
                            print ("Diacritice: " + wrd)

                    if wrd in list_carne or wrd in list_branduri_carne:
                        Produs_Catego[produ] = "carne,"
                        break
                    if wrd in list_branduri_cereale:
                        Produs_Catego[produ] = "faina; gris; malai,"
                        break
                    if wrd in list_branduri_acool:
                        Produs_Catego[produ] = "gustari si bauturi,"
                        break
                    if wrd in list_branduri_lactate:
                        Produs_Catego[produ] = "lactate,"
                        break
                    if wrd in list_branduri_gustari:
                        Produs_Catego[produ] = "gustari si bauturi,"
                        break
                    if wrd in list_branduri_condimente or wrd in list_condimente:
                        Produs_Catego[produ] = "condiment,"
                        break
                    if wrd in list_legume:
                        Produs_Catego[produ] = "leguma,"
                        break
                    if wrd in list_fructe:
                        Produs_Catego[produ] = "fruct,"
                        break

                    if wrd == "dovlecei":
                        wrd = "dovleac"

                    print("Apel ro word net: " + wrd)
                    categorie_finala = ro_word_net(wrd)
                    if categorie_finala != "none" and categorie_finala is not None:
                        Produs_Catego[produ] = (categorie_finala +",")
                        break
                    else:
                        Produs_Catego[produ] = "Nici o categorie gasita,"

    if Produs_Catego:
        print(Produs_Catego)
        return Produs_Catego
    else:
        return "Detalii"


