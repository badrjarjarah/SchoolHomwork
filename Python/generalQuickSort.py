# a general quick sort algorithm
def swap(L,index1,index2):
    temp = L[index1]
    
    L[index1] = L[index2]
    L[index2] = temp

def partition(L,first,last):
    p = first
    
    for current in range(p+1,last+1):
        
        if L[current] < L[p]:
            p
            swap(L,current,p+1)
            swap(L,p,p+1)
            p = p +1
    return p

def generalQuickSort(L,first,last,k):
    
    if first < last:
        p = partition(L,first,last)
    
    if sorted(L[:k]):
        return L[:k]
    
    generalQuickSort(L,first,p-1)
    generalQuickSort(L,p+1, last)
    return L