import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { SubscriptionService } from 'Frontend/generated/endpoints';
import { useEffect } from 'react';

export const config: ViewConfig = {
  menu: { order: 0, icon: 'line-awesome/svg/file.svg' },
  title: 'Empty',
  loginRequired: true,
};

export default function EmptyView() {
  const lastUpdate = useSignal("Waiting...");
  useEffect(() => {
    const subscription = SubscriptionService.subscribe().onNext(update => lastUpdate.value = update);
    return () => subscription.cancel();
  }, []);

  return (
    <div className="flex flex-col h-full items-center justify-center p-l text-center box-border">
      <img style={{ width: '200px' }} src="images/empty-plant.png" />
      <h2>This place intentionally left empty</h2>
      <p>It’s a place where you can grow your own UI 🤗</p>
      <p>{lastUpdate}</p>
    </div>
  );
}
