import rowordnet

wn =  rowordnet.RoWordNet()

word = 'morcov'
synset_ids = wn.synsets(literal=word)
# print("Total number of synsets containing literal '{}': {}".format(word, len(synset_ids)))
# print(synset_ids)

for synset_id in synset_ids:
    list_hypernym = wn.list_hypernyms(synset_id)
    for hypernym in list_hypernym:
        wn.print_synset(hypernym)
    # wn.print_synset("ENG30-07843775-n")
    # wn.print_synset("ENG30-13100677-n")
    # print("\nPrint all outbound relations of {}".format(wn.synset(synset_id)))
    # outbound_relations = wn.outbound_relations(synset_id)
    # for outbound_relation in outbound_relations:
    #     target_synset_id = outbound_relation[0]        
    #     relation = outbound_relation[0]
    #     print("\tRelation [{}] to synset {}".format(relation,wn.synset(target_synset_id)))
    #     relation = outbound_relation[1]
    #     print("\tRelation [{}] to synset {}".format(relation,wn.synset(target_synset_id)))10