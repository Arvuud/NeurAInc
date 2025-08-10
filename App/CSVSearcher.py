import csv

def search_in_csv(file_path, search_term):
    with open(file_path, mode='r') as file:
        reader = csv.reader(file)
        for row in reader:
            if search_term in row:
                return True
    return False
