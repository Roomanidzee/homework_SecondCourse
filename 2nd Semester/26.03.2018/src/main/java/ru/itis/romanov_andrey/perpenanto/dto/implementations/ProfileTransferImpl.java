package ru.itis.romanov_andrey.perpenanto.dto.implementations;

import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ProfileTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProfileTransferImpl implements ProfileTransferInterface{

    @Override
    public List<TempProfile> getTempProfiles(List<Profile> oldList) {

        int profilesCount = oldList.size();

        List<Long> ids = new ArrayList<>(profilesCount);
        List<String> names = new ArrayList<>(profilesCount);
        List<String> surnames = new ArrayList<>(profilesCount);
        List<Long> userIds = new ArrayList<>(profilesCount);
        List<Long> addressIds = new ArrayList<>(profilesCount);

        int count = 1;
        int i1 = 0;

        while(i1 < profilesCount){

            ids.add((long) count);

            count++;
            i1++;

        }

        IntStream.range(0, profilesCount).forEachOrdered(i -> {
            names.add(oldList.get(i).getPersonName());
            surnames.add(oldList.get(i).getPersonSurname());
            userIds.add(oldList.get(i).getUser().getId());
            addressIds.add(oldList.get(i).getAddresses().get(0).getId());
        });

        return IntStream.range(0, profilesCount)
                        .mapToObj(i -> TempProfile.builder()
                                                  .id(ids.get(i))
                                                  .personName(names.get(i))
                                                  .personSurname(surnames.get(i))
                                                  .userId(userIds.get(i))
                                                  .addressId(addressIds.get(i))
                                                  .build()
                                 )
                        .collect(Collectors.toList());

    }
}
